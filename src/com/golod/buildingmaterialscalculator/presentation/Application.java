package com.golod.buildingmaterialscalculator.presentation;

import com.golod.buildingmaterialscalculator.domain.model.User;
import com.golod.buildingmaterialscalculator.service.operations.AuthorizationService;

public class Application {
  public static User currentUser;

  public static void runner() throws IllegalAccessException {
    Menu.show();
    // Авторизація користувача
    AuthorizationService.authorization();

    if (currentUser != null) {
      System.out.println("Поточний користувач: " + currentUser.getUsername());
    }
  }

  public static void main(String[] args) throws IllegalAccessException {
    runner();
  }
}
