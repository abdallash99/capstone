# Capstone Project Backend
[Front End Repository](https://github.com/abdallash99/capstone_frontend)

used technology:
* React
* Spring
* Java
* Kubernetes 
* Jenkins
* EKS from AWS
* Bootstrap
* Git & Github
* Maven
* Docker

[Frontend Live preivew](http://ae8a2dc34295e40f0816eae657ba1a12-1120369785.eu-west-1.elb.amazonaws.com)
[Backend URL](http://a3e735911c7d34404806c9eb25e1be93-1776962881.eu-west-1.elb.amazonaws.com)
## Code Overview

### 1. Effective Java 
1. Item 1: As you can see in WallFactory class I use Factory Method to create new Wall instead of using Constructor to create them.
2. Item 4: In all Class that shouldn't create Object from them I add a private constructor you can see this in WorldNavigatorBuilder class
```java
public class WorldNavigatorBuilder {

    private WorldNavigatorBuilder() { }
    //...
}
```
3. Item 5: In all the code that I write I use DI, I inject the inner object value via a constructor, in wall factory method you can just give the function the type of the Wall and the inner object for lock and inventory, and it will return wall object with given parameter.
```java
public class WallFactory {
    private WallFactory() {
    }

  public static Wall getWall(String wallType, Inventory inv, Openable lock) {
        if (wallType.equalsIgnoreCase("Painting")) {
          return new Painting(lock, inv);
        }
    //...
    }
}
```
4. Item 6: I try my best on don't create an unnecessary object and intelli assist me on that it have an excellent warning highlight this is an example of that

```java
//here I create single instance Of random add it on the top level of the project so any class can use it
public static final Random random=new Random();
```
5.I don’t override equal method if I don’t have to do that I override it in the all Portable classes because I use remove method in inv array list and instead of I compare the inner value of the key I use equals method and if I want to change the inner implementation at any time just change equals method as you see in the following code, I use generated equals by IntelliJ, after I used it I guess it adhere all general contract
```java
public String use(Key key) {
        if (this.key.equals(key)) {
            isLocked = false;
            return "Unlocked Successfully";
        } else return "This key is not suitable for the lock";
    }
```
6. Item 11: Each Time i generate equals method I generate hashcode.

7. Item 12: I Generate toString For All class in this project.

8. Item 15: I try to minimize the accessibility of classes and members as I can, but in some cases and because of using packaging some class can't be private.

9. Item 16: All fields is a private member with a public getter and setter.

10. Item 17: as i can i define the member variable as final without effect the implementation. 

11. Item 18: I don't use inheritance without a good reason and all functionality that I add to class come from composition not from inheritance like Wall abstract class it have lock and inventory feature throw composition 

```java
public abstract class Wall {
    private final Openable lock;
    private final Inventory inv;
    //...
}
```

12. Item 20: I use Interface for define many features, but the Wall class can't be interfaced because of that I use abstract class.

13. Item 22: I use Interface to define type for example class Inventory used to define new type that can be used to loot items from the map.  
```java
public interface Inventory {
    String list();

    String loot();

    Collection<Portable> check();

    String buy(String type);

    String sell(String type);
}
```
14. Item 24: because of using non-static member will lead to create more object each time the user create object from that class I try to reduce the number of non-static member as I can without effect the implementation.

15. Item 25: All class I created I create one file for it.

16. Item 26: I use List<E> instead of using List with old java style each time I want to define collection datastructures, using of old java style will lead to wrong use or security issue.

17. Item 28: I use all collections defined datastructures instead of using array or define my own ones this give me less time of testing and more flexibility that collections give us, although using List give me real time error highlight instead see the error on runtime.

18. Item 34: I use enum to define constant, like in the code below.
```java
package com.capstone.project.worldnavigator;

public enum DirectionsMove {
    UP(-1,0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    DirectionsMove(int x, int y) {
        this.x=x;
        this.y=y;
    }

}
```
so here as you can see I define the movement of the player using enum insted of just add the number on an array this make the code more readable and maintainable.

19. Item 40: by default intelji add `@Override` annotation to any function that override from its parent.

20. Item 51: As a clean code principle and here the writer say the same thing, in my code I reduce the number of parameter as I can and named the function carefully, so it's describe what it should do.

21. Item 54: when function don't have thing to return I use empty ArrayList or empty thing but not null.

22. Item 58: I use foreach instead of using the traditional for loop, and some time I use stream foreach it's give me excellent small code.

23. Item 59: Instead of using servlet and I use spring boot with all the functionality that give to me, for example the spring come with hibernate it's very helpful, after using it I don't need to deal with database command or creating table.

24. Item 62: I used string just to represent string and don't use it for another purpose. 

25. Item 63: As you can see in the following code I use StringBuilder to code concatenation to avoid creating unnecessary object with low performance.
```java
public String loot() {
    StringBuilder s = new StringBuilder();
    for (var element : this.invList) {
      s.append(element).append(" Is Looted\n");
    }
    invList.clear();
    return s.toString();
  }
```
26. Item 64: In all code I use interface to refer to object, but in player class i use the class itself because the player always have inventory and use the interface here will lead to reduce the feature of the class.

27. Item 68: I use the name convention for all thing for example class, all class started with capital latter and use camel case and so on.

### 2. Clean Code:
#### Comments:
1. C3 Redundant Comment: I try to make the code self explained as I can and because of that you will not see any comment in my code.

2. C5 Commented-Out Code: all unusable code or unneeded deleted from the project.

#### Environment:

* because I use a maven with the spring I don't worry about building or testing the application and with jenkins all of these things done just buy on button.

#### Functions:
1. F1 Too Many Arguments: as I say in the previews section all functions have less than three argument, and I try to reduce the number of parameter.

2. F2 Output Arguments,F3 Flag Arguments : In all function that I create, I talk care of the parameter, so it's can't do more than what it should do.

#### General:
1. G4 Overridden Safeties: In the code I try to remove all warning by do what I should do not by disable it.
2. G5 Duplication: In the code I try to keep the code minimum as possible and every piece of code that repeated more than one time I try to redundant the code.
3. G6 Code at Wrong Level of Abstraction: by using of Factory design pattern and Strategy design pattern I guss that my abstraction is done in the right way.
4. G7 Base Classes Depending on Their Derivatives: Parent class don't know anything about there children on my code.
5. G8 Too Much Information: Make the code with fewer functions is not easy thing because of at the same time you need to stratify single purpose principle, I try to make less function but at the same time don't give the function more than one job.
6. G11 Inconsistency: I try to make all of my code with the same conventions, and review the code a lot of time for that, but it might happen.
7. G15: Selector Arguments: I don't use boolean as argument I just use it as constructor parameter.
8. G16 Obscured Intent: This is an example of random code that I wrote, naming of the function describe what it does without do a lot of effort to understand it.
```java
public String check() {
        if (frontWall.getLock().isLocked()) {
            return frontWall.getLock().check();
        } else if (!frontWall.getInv().check().isEmpty()) {
            getInv().addAll(frontWall.getInv().check());
            return frontWall.getInv().loot();
        }
        return "No Item Here";
    }
```
9. G17 Misplaced Responsibility: On my code I write every function in the right place (at least I guss that), and by using packaging you don't need a lot of time to find what you're searching for in it.
10. G19 Use Explanatory Variables: I try to name the local variable to be self explained as I can, this is example:
```java
Point currentPosition = player.getCurrentPosition();
```

11. G20 Function Names Should Say What They Do: as I say before good function name give the code more readability, for example look at code in point 8.

12. G23 Prefer Polymorphism to If/Else or Switch/Case: I try to reduce if statement as I can, and use polymorphism like when you want to open something the code don't check the type of it using strategy design pattern reduce a lot of if/else statements.

13. G25 Replace Magic Numbers with Named Constants:  as you can see here I define all constant variable with a good name, and some of them give them enum value based on effective java items.   
```java
public class ProjectApplication {
    public static final Random random=new Random();
    public static final int HEIGHT = random.nextInt(20) + 50;
    public static final int WIDTH = random.nextInt(20) + 50;
    public static final int GAME_PERIOD = 300000;
    public static final int NUMBER_OF_PLAYER=2;
    public static final int ROOM_WALL_NUMBER = 4;
    public static final DirectionsMove[] MOVE_RATE = {
            DirectionsMove.UP,
            DirectionsMove.RIGHT,
            DirectionsMove.DOWN,
            DirectionsMove.LEFT
    };

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }


}

```

14. G28 Encapsulate Conditionals: as you can see here I separate the condition in boolean function that can check the condition.

```java
if (canMove(currentPosition, newDirection))
```

15. G29 Avoid Negative Conditionals: I try to do that, but sometime when I use Collections for example `isEmpty()` function, and I want to check if list is empty, here I should use the bad type.

16. G30 Functions Should Do One Thing: I try to create function with one job, and this is a part of single responsibility principle.

17. G35 Keep Configurable Data at High Levels: if you look o the code in point 15 you will see that all configuration variable is declared here in the main class.

#### Java
1. J1 Avoid Long Import Lists by Using Wildcards: At all of my code when I import I try to not import every thing but there is situation I should import it with it name like List because java.awt and java.util have List.
2. J3 Constants versus Enums: We talk about this in effective java items.
#### Name
* N1 Choose Descriptive Names: I try to name variable good as I can.

### Design pattern 
#### Factory design pattern
I used Factory design pattern for creating new Wall object, my Wall factory class have one method that can create new Wall objects from any wall type that extends wall, below the code. Figure 17 Wall Factory class Figure 18 using of wall factory As you can see above I use overriding on wall factory class I don’t sure if it’s the best practice, this wall factory will give you a very  good DI as I will take about that in next chapter, for now I used Wall factory on a constructer of the World Navigator to generate JSON file at next future I
```java
public class WallFactory {
    private WallFactory() {
    }
    //this is the implementation of the factory 
    public static Wall getWall(String wallType, Inventory inv, Openable lock) {
        if (wallType.equalsIgnoreCase("Painting")) {
            return new Painting(lock, inv);
        } else if (wallType.equalsIgnoreCase("Door")) {
            return new Door(lock, inv);
        } else if (wallType.equalsIgnoreCase("Mirror")) {
            return new Mirror(lock, inv);
        } else if (wallType.equalsIgnoreCase("Seller")) {
            return new Seller(lock, inv);
        } else if (wallType.equalsIgnoreCase("Chest")) {
            return new Chest(lock, inv);
        } else if (wallType.equalsIgnoreCase("Wall")) {
            return new BlankWall(lock, inv);
        } else {
            return new NullWall(lock, inv);
        }
    }
}

```

```java
//example of use of the factory
WallFactory.getWall("Door",new WithoutInv(),new WithLock(false));
```
#### Strategy Design Pattern
This design pattern give my code a lot of power, after I use it I don't worry about a lot of  if statement here is an example:
```java
 public String use(Key key) {
        if (this.key.equals(key)) {
            isLocked = false;
            return "Unlocked Successfully";
        } else return "This key is not suitable for the lock";
    }
```
```java
    public String use(Key key) {
        return "Can't use the key in non lock wall";
    }
```
Here if we invoke `use` function in Openable interface if the wall have Lock the function will work fine else the function will return message to tell the player this wall doesn't have lock to use it with.

### SOLID principle 

####  Single responsibility principle:
I talk a lot about this principle because the two book writer talk about it in their book, I reduce the responsibility for each class and function as I can

####  Open Close Principle:
Design pattern give me awesome open/close  for example if I want to add new type of Lock on the wall the only thing that I should do is to create new class and give object of that class throw the wall factory and all thing will done for you, another example if you want to add new type of portable just add it nothing else you should do if it don’t effect the map, for example if I want to add axe so the player can break wall instead of open the door you just add new function on lock class this function change isBlock to false.

#### Liskov Substitution Principle: 
As I used strategy pattern this give me the same thing that we talk about it in the lecture, but not in the same way in my code the user might use check for example on non-check wall, because of that I use class and all function in it will return error message for the user instead of check validity and then call the function.

#### Interface Segregation Principle:
I guess my code don’t satisfy this principle on the best approach for example at any time, there might be new modification on inventory, so some type of the inv can’t sell or can’t buy but in this case we can create new inventory class and disable selling or buying in it.

#### Dependency Inversion:
As I talk in the previews chapters all of my constructor talk object as parameter does not create that object on the constructor, I try to do this in the best way, so I create WallFactory class as  I said before, I use Dependency Inversion in the best way to create a new map, you can check WorldNavigatorBuilder class and see how I use it, in addition spring have built in DI and I use it like the following code.
```java
 @Autowired private GameService gameService;
```

###  Google style code
For this thing I use Google style plugin for style all my code.

### Datastructures
I used three main type of datastructures.
1. `List` I use it for create the map and because concurrency issue I use  `Collections.synchronizedList`.
2. `Map` I use map because of the power of find in it take O(1) time to find item.
3. `Set` I use set to the same reason of use `Map`

## DevOps
### Git and Github
I use git as version control and use github to host my code on it
### jenkins 
I create jenkins server on aws that talk code from github repository and build the code using maven and create an image and after all push it to docker hub.
For frontend, I don't create one for now I will create one soon.
### Docker 
I use Docker to create image and run container from it, you can check docker file here to see how I create the image.

### Kubernetes 
I use kubernetes as orchestration tools for docker container and I use EKS from AWS to run it in the cloud I will add folder with all required .yaml file.

## orchestration and containerization of My project
I create Deployment for a backend and another one for frontend with this deployment I create two service with load balancer and this service give me two url, I connect backend with front end.
I create postgresql host, but I don't write the code by me I git the code to run it from internet if you want to know more please open K8 folder you can find all file there  
