# JPA_N+1_문제

## N+1 문제란
- 1개의 쿼리를 예상하고 Jpa 코드를 실행했는데 예상치 못하게 N개의 쿼리가 추가로 발생하는 문제입니다.
- 1:N, N:1 연관 관계를 가지고 있을 때 발생합니다.

## 코드 설명
- Team과 Member는 1:N 연관관계를 가지고 있습니다.
  - Member에서 Team은 Lazy Loading으로 호출됩니다.
- Member의 모든 값을 findAll로 찾아온 뒤 각 멤버의 팀이름을 호출합니다.

## 기타
- spring data jpa를 사용해도 동일합니다. spring data jpa역시 내부적인 동작방식은 동일(jpql을 사용)하기 때문입니다.
- N+1문제는 DB에서 값을 가져올 때 일어나는 문제입니다. 영속성 컨텍스트에 값이 남아있으면 테스트를 제대로 할 수 없습니다. <br>
  테스트코드가 시작하기 전에 `em.flush()`로 영속성 컨텍스트의 값을 DB에 반영하고, `em.clear()`로 영속성 컨텍스트를 초기화 했습니다.
- 데이터 생성 시 10명의 member가 모두 동일한 team을 가지고 있다면 N번의 쿼리가 아닌 1번의 쿼리만 추가적으로 더 실행됩니다. <br>
  그 이유는 영속성 컨텍스트에 등록된 데이터를 계속 활용하기 때문에 더이상 디비에 쿼리를 요청하지 않기 때문입니다. <br>
  이는 근본적으로 N+1문제를 해결한 게 아닙니다. 그래서 예제는 N+1쿼리를 시각적으로 명확하게 보기 위해 member마다 다른 team을 가집니다.

## 실행 방법
- NplusOne > src > test > java > qwerty1434.NplusOne > NplusOneTest> NplusOne테스트를 실행하면 됩니다.

## 실행 결과
![image](https://user-images.githubusercontent.com/25142537/218299311-55011ffe-c64d-4e4b-b8ba-a74787778775.png)

## 설명
- 우리는 DB에서 값을 꺼내오는 코드를 하나만 사용했습니다. `memberRepository.findAll()`만 사용했기 때문에 쿼리가 1번만 실행되기를 기대합니다.
- 하지만 멤버에 있는 팀 정보를 꺼내기 위해 추가 쿼리가 실행됩니다. 
  - Lazy Loading이기 때문에 `m.getTeam().getTeamName()` 시점에 데이터를 가져옵니다. <br>
    Eager Loading이라면 findAll()시점에 즉시 쿼리가 발생할 뿐 달라지는 건 없습니다.
- Member데이터를 가져올 때 Member데이터와 함께 쓰일 Team데이터를 `한방에 가져오지 않아서` N+1문제가 발생합니다. <br>
  한방에 가져오는 것과 즉시 로딩은 다릅니다. 한방에 가져온다는 건 하나의 쿼리로 값을 가져온다는 의미입니다. <br>

# JPA_N+1_해결방법

## Fetch Join이란
- fetch join은 SQL에서 제공하는 기능이 아닙니다. JPQL에서 성능 최적화를 위해 제공하는 기능입니다.
- 연관된 엔티티나 컬렉션을 한번에(쿼리 하나로) 조회합니다.

## 실행 방법
- NplusOne > src > test > java > qwerty1434.NplusOne > NplusOneTest> SolveNplusOne테스트를 실행하면 됩니다.

## 실행 결과
![image](https://user-images.githubusercontent.com/25142537/218300212-2336493e-a8c4-45c5-8bf9-2fa8911bb887.png)

## 설명
- fetch join을 사용해 N+1 문제를 해결했습니다.
- 실행한 쿼리를 살펴보면 `select구문에 member뿐 아니라 team도 같이 가져오는 걸 확인할 수 있습니다.`
  <br> 이처럼 select구문에 변수를 넣어 모든 데이터 한번에 가져오는 게 fetch join의 특징입니다. (다시한번 말하지만 Eager와는 다릅니다.)
- fetch join을 사용하면 `Entity의 로딩 전략(Eager,Lazy)을 무시`하고 필요한 모든 엔티티를 조인한 뒤 한방쿼리로 가져와 버립니다.
