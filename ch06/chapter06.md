# Chapter06 영속 객체의 라이프사이클

## 개요

```java
//EntityManager 생성
EntityManager em = EMF.currentEntityManager();
try {
  //Transaction 시작
  em.getTransaction().begin();
  
  //User entity 조회
  User user = em.find(User.class, email);
  if (user == null) throw new UserNotFoundException();
  
  //User entity 수정
  user.changeName(newName);
  
  //Transaction 커밋
  em.getTransaction().commit();
} catch (Exception ex) {
  
  //Transaction 롤백
  em.getTransaction().rollback();
} finally {
  
  //EntityManager 종료
  em.closeCurrentEntityManager();
}
```

위 코드는 EntityManager.find()로 User 타입의 엔티티 객체를 구한다. 12번째 줄에서 user 객체를 수정한다. 15번째 줄에서 **트랜잭션 커밋 시점에 user 객체의 변경 내역을 DB에 저장**한다. 

여기서 user 객체는 영속 컨텍스트에 보관된 영속 객체이다. JPA는 영속 컨텍스트에 보관된 객체의 변경 내역을 추적한다. 이렇게 JPA의 영속 컨텍스트를 통해서 관리되는 상태의 영속 객체는 관리됨(managed) 상태를 갖는다.

EntityManager가 종료되면 영속 컨텍스트가 사라진다. 이 시점에서 user 객체는 영속 컨텍스트와의 연결이 끊어지는데 이때 user 객체는 분리됨(detached) 상태가 된다. 분리됨 상태의 영속 객체는 변경 내역이 추적되지 않으므로, 필드를 변경해도 변경 내역이 DB에 반영되지 않는다.

EntityManager.remove() 메서드에 관리 상태의 객체를 전달하면 삭제됨(removed) 상태가 된다. 영속 컨텍스트에 보관된 영속 객체가 삭제됨 상태가 되면 트랜잭션 커밋 시점에 해당 데이터를 DB에서 삭제한다.



## persist()와 관리 상태 객체

EntityManager.persist()를 이용해서 영속 컨텍스트에 엔티티 객체를 추가하면, 해당 엔티티 객체는 관리 상태가 된다. 영속 컨텍스트에서 엔티티 객체를 관리할 때 식별자를 기준으로 각 엔티티를 구분한다. 즉, 영속 컨텍스트에 엔티티 객체를 보관하려면 해당 엔티티는 식별자를 가져야 한다.

이런 이유로 EntityManager.persist()를 이용해서 엔티티 객체를 추가할 때 엔티티의 식별자 생성 방식에 따라 insert 쿼리를 실행하는 시점이 달라진다.

1. auto_increment
   - 데이터를 DB에 삽입할때 식별자가 할당된다.
   - persist() 할 때 insert 쿼리가 실행된다.
2. TableGenerator
   - persist() 할 때 식별자를 생성하는 쿼리가 실행된다.
   - insert 쿼리는 커밋할 때 실행된다.
3. 시퀀스
   - 테이블 제너레이터 방식과 마찬가지로 persist() 할 때 시퀀스에서 값을 구하는 쿼리를 실행한다.
   - insert 쿼리는 커밋할 때 실행된다.
4. 직접 할당
   - 직접 식별자를 할당하는 경우에는 persist() 할 때 이미 식별자가 존재하므로 별도의 쿼리가 작동하지 않는다.
   - insert 쿼리는 커밋할 때 실행된다.

주의할 점

- 트랜잭션 범위에서 실행하지 않으면 엔티티를 DB에 추가하는 insert 쿼리가 실행되지 않는다.
- persist() 이후에 엔티티 객체의 상태를 변경하면, managed 상태이기 때문에 트랜잭션을 커밋할 때 변경 내역을 함께 DB에 반영한다.
- persist()로 엔티티 객체를 영속 컨텍스트에 추가하면, 이는 캐시에 엔티티 객체가 보관된다는 것을 뜻한다. 이후에 find()로 엔티티 객체를 구하면 select 쿼리를 사용하지 않고, persist()로 저장한 객체를 리턴한다.



## find()와 관리 상태 객체

EntityManager.find()로 구한 객체도 영속 컨텍스트에 보관되어 관리 상태가 된다. 관리 상태의 영속 객체는 트랜잭션 범위에서 상태가 바뀌면 트랜잭션을 커밋하는 시점에 변경 내역을 반영하기 위한 update 쿼리를 실행한다. 

트랜잭션을 사용하지 않으면 EntityManager를 닫기 전에 객체를 변경ㅎ도 변경 내역이 DB에 반영되지 않는다.

동일 식별자를 갖는 엔티티를 다시 find()로 구하면 select 쿼리가 실행되지 않고 영속 컨텍스트에 보관된 엔티티 객체를 리턴한다.



## 분리 상태 (detached) 객체

영속 컨텍스트에 보관된 영속 객체는 EntityManager가 종료되면 분리 상태가 된다. 

분리 상태가 되면 객체의 상태를 변경해도 DB에 반영하지 않는다. 

```java
//EntityManager.detach() 메서드를 사용하면 관리 상태의 객체를 강제로 분리 상태로 만들 수 있다.

em.getTransaction().begin();
User user = em.find(User.class, "smjang@imex.co.kr");

// 강제 분리
em.detach(user);

// 분리 상태가 되어 변경 상태를 추적하지 않는다.
user.changeName("Aris");

em.getTransaction().commit();
em.close();
```

detach()로 관리 상태의 객체를 영속 컨텍스트에서 분리하면 더는 변경 상태를 추적하지 않는다. 따라서 detach()로 분리하면 트랜잭션 범위에서 엔티티 객체의 상태를 변경해도 트랜잭션 커밋 시점에 변경 내역이 반영되지 않는다.

## merge()로 분리 상태를 관리 상태로 바꾸기

```java
EntityManager em = EMF.createEntityManager();
User user = null;
try {
  user = em.find(User.class, "smjang@imex.co.kr");
} finally {
  em.close();
}
// 이 시점에서 user 객체는 분리 상태이다.
// 분리 상태에서 객체의 상태를 변경
user.changeName("Aristata");

EntityManager em2 = EMF.createEntityManager();
try {
  em2.getTransaction().begin();
  em2.merge(user); //user 객체는 다시 관리 상태가 되었다.
  em2.getTransaction().commit(); // 커밋 시점에 변경 내역 반영
} catch (Exception ex) {
  em2.getTransaction().rollback();
} finally {
  em2.close();
}
```



## 삭제 상태 객체

```java
EntityManager em = EMF.createEntityManager();
try {
  em.getTransaction().begin();
  user = em.find(User.class, "smjang@imex.co.kr");
  em.remove(user); // user는 삭제 상태가 된다.
  
  em.getTransaction().commit(); // 커밋 시점에 delete 쿼리가 실행된다. 
} catch (Exception ex) {
  em.getTransaction().rollback();
} finally {
  em.close();
}
```

관리 상태 영속 객체를 EntityManager.remove() 메서드에 전달하면 삭제 상태로 바뀐다. 트랜잭션 커밋 시점에서 delete 쿼리가 싱행되고 DB에서 삭제된다. 삭제 상태로 바뀐 엔티티를 다시 merge()에 전달하면 예외가 발생한다. 















