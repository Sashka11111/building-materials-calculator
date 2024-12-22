package com.golod.buildingmaterialscalculator.service.validation;

import com.golod.buildingmaterialscalculator.domain.model.Calculation;

import java.util.ArrayList;
import java.util.List;

public class CalculationValidator {

  // Метод для валідації об'єкта Calculation
  public static List<String> validate(Calculation calculation) {
    List<String> errors = new ArrayList<>();

    // Перевірка ідентифікатора
    if (calculation.getId() == null ) {
      errors.add("Ідентифікатор розрахунку не може бути порожнім.");
    }

    // Перевірка ідентифікатора користувача
    if (calculation.getUserId() == null) {
      errors.add("Ідентифікатор користувача не може бути порожнім.");
    }

    // Перевірка типу будівлі
    if (calculation.getBuildingType() == null || calculation.getBuildingType().isEmpty()) {
      errors.add("Тип будівлі не може бути порожнім.");
    }

    // Перевірка площі
    if (calculation.getArea() <= 0) {
      errors.add("Площа будівлі повинна бути більшою за нуль.");
    }

    // Перевірка периметра
    if (calculation.getPerimeter() <= 0) {
      errors.add("Периметр будівлі повинен бути більшим за нуль.");
    }

    // Перевірка результатів розрахунків (якщо потрібно)
    if (calculation.getResults() == null || calculation.getResults().isEmpty()) {
      errors.add("Результати розрахунків не можуть бути порожніми.");
    }

    return errors; // Повертає список помилок (якщо є)
  }
}
