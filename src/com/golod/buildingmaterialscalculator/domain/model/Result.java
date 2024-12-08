package com.golod.buildingmaterialscalculator.domain.model;

import java.util.Objects;

public class Result {
  private String calculationId; // Ідентифікатор розрахунку
  private String materialId;    // Ідентифікатор матеріалу
  private double amount;        // Кількість матеріалу
  private double totalCost;     // Загальна вартість матеріалу
  private String materialName;  // Назва матеріалу
  private String unit;          // Одиниця виміру (кг, м2, шт)

  // Конструктор
  public Result(String calculationId, String materialId, double amount, double totalCost, String materialName, String unit) {
    this.calculationId = calculationId;
    this.materialId = materialId;
    this.amount = amount;
    this.totalCost = totalCost;
    this.materialName = materialName;
    this.unit = unit;
  }

  // Гетери та сетери
  public String getCalculationId() {
    return calculationId;
  }

  public void setCalculationId(String calculationId) {
    this.calculationId = calculationId;
  }

  public String getMaterialId() {
    return materialId;
  }

  public void setMaterialId(String materialId) {
    this.materialId = materialId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  public String getMaterialName() {
    return materialName;
  }

  public void setMaterialName(String materialName) {
    this.materialName = materialName;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result result = (Result) o;
    return Double.compare(result.amount, amount) == 0 &&
        Double.compare(result.totalCost, totalCost) == 0 &&
        Objects.equals(calculationId, result.calculationId) &&
        Objects.equals(materialId, result.materialId) &&
        Objects.equals(materialName, result.materialName) &&
        Objects.equals(unit, result.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(calculationId, materialId, amount, totalCost, materialName, unit);
  }

  @Override
  public String toString() {
    return "Result{" +
        "calculationId='" + calculationId + '\'' +
        ", materialId='" + materialId + '\'' +
        ", amount=" + amount +
        ", totalCost=" + totalCost +
        ", materialName='" + materialName + '\'' +
        ", unit='" + unit + '\'' +
        '}';
  }
}
