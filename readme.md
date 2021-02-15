# Capstone Project Backend
[Front End Repository](https://github.com/abdallash99/capstone_frontend)

## Code Overview

### Effective Java 
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

27. Item 68: I use the name convention for all thing like class, all class started with capital latter and use camel case and so on.

