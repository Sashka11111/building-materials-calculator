package com.golod.buildingmaterialscalculator.service.validation;

import com.golod.buildingmaterialscalculator.domain.model.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultValidator {

  // Метод для валідації об'єкта Result
  public static List<String> validate(Result result) {
    List<String> errors = new ArrayList<>();

    // Перевірка ідентифікатора розрахунку
    if (result.getCalculationId() == null || result.getCalculationId().isEmpty()) {
      errors.add("Ідентифікатор розрахунку не може бути порожнім.");
    }

    // Перевірка ідентифікатора матеріалу
    if (result.getMaterialId() == null || result.getMaterialId().isEmpty()) {
      errors.add("Ідентифікатор матеріалу не може бути порожнім.");
    }

    // Перевірка кількості матеріалу
    if (result.getAmount() <= 0) {
      errors.add("Кількість матеріалу повинна бути більшою за нуль.");
    }

    // Перевірка загальної вартості матеріалу
    if (result.getTotalCost() <= 0) {
      errors.add("Загальна вартість матеріалу повинна бути більшою за нуль.");
    }

    // Перевірка назви матеріалу
    if (result.getMaterialName() == null || result.getMaterialName().isEmpty()) {
      errors.add("Назва матеріалу не може бути порожньою.");
    }

    // Перевірка одиниці виміру
    if (result.getUnit() == null || result.getUnit().isEmpty()) {
      errors.add("Одиниця виміру не може бути порожньою.");
    }

    return errors; // Повертає список помилок (якщо є)
  }
}

