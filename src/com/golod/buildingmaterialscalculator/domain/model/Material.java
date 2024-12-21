package com.golod.buildingmaterialscalculator.domain.model;

import java.util.Objects;

public class Material {
  private String id; // Унікальний ідентифікатор матеріалу
  private String name; // Назва матеріалу
  private String unit; // Одиниця виміру (наприклад, "кг", "шт", "м2")
  private double unitPrice; // Ціна за одиницю
  private double unitSize; // Розмір одиниці (для блоків, покрівлі тощо)
  private Category category; // Категорія, яка є об'єктом класу Category

  public Material() {
  }
  public Material(String id, String name, String unit, double unitPrice, double unitSize, Category category) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.unitPrice = unitPrice;
    this.unitSize = unitSize;
    this.category = category;
  }

  // Гетери та сетери
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public double getUnitSize() {
    return unitSize;
  }

  public void setUnitSize(double unitSize) {
    this.unitSize = unitSize;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Material material = (Material) o;
    return Double.compare(material.unitPrice, unitPrice) == 0 &&
        Double.compare(material.unitSize, unitSize) == 0 &&
        Objects.equals(id, material.id) &&
        Objects.equals(name, material.name) &&
        Objects.equals(unit, material.unit) &&
        Objects.equals(category, material.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, unit, unitPrice, unitSize, category);
  }

  @Override
  public String toString() {
    return "Material{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", unit='" + unit + '\'' +
        ", unitPrice=" + unitPrice +
        ", unitSize=" + unitSize +
        ", category=" + category +
        '}';
  }
}
