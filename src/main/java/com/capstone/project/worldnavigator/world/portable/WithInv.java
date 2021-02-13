package com.capstone.project.worldnavigator.world.portable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WithInv implements Inventory {
  private final List<Portable> invList;
  private final Gold gold;
  private Light light;

  public WithInv() {
    invList = Collections.synchronizedList(new ArrayList<>());
    light = new Light();
    gold = new Gold();
  }

  public Gold getGold() {
    return gold;
  }

  public List<Portable> getInvList() {
    return invList;
  }

  public void add(Portable item) {
    if (item instanceof Light&&!light.isHaseLight()) {
      light = (Light) item;
    } else if (item instanceof Gold) {
      gold.add(item.getPrice());
    }else this.invList.add(item);
  }

  public void addAll(Collection<Portable> inv) {
    for (var element : inv) add(element);
  }

  public void remove(Portable item) {
    if (item instanceof Light) {
      light = new Light();
    } else if (item instanceof Gold) {
      gold.add(-item.getPrice());
    }
    this.invList.remove(item);
  }

  public String sell(String type) {
    Portable item = search(type);
    if (item.getPrice() == -1) return "No Item With This type";
    remove(item);
    gold.add(item.getPrice());
    return "You Sell " + item;
  }

  private Portable search(String type) {
    if (type.equalsIgnoreCase("light") && light.isHaseLight()) {
      return light;
    } else {
      Key key = new Key(type);
      for (var element : invList) {
        if (element.equals(key)) {
          return element;
        }
      }
    }
    return new NullPortable();
  }

  public String buy(String type) {
    Portable item = search(type);
    if (item.getPrice() == -1) return "No Item With This type";
    if (item.getPrice() >= gold.getPrice()) {
      add(item);
      gold.add(-item.getPrice());
      return "You Bought The Item Successfully";
    } else return "You Don't Have Enough Money";
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (var item : invList) {
      s.append(item.toString()).append("\n");
    }
    return s.toString();
  }

  public Light getLight() {
    return light;
  }

  @Override
  public String list() {
    return this.toString();
  }

  @Override
  public String loot() {
    StringBuilder s = new StringBuilder();
    for (var element : this.invList) {
      s.append(element).append(" Is Looted\n");
    }
    invList.clear();
    return s.toString();
  }

  @Override
  public Collection<Portable> check() {
    return invList;
  }
}
