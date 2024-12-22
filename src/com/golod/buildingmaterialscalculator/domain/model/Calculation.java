package com.golod.buildingmaterialscalculator.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Calculation {
  private UUID id; // Унікальний ідентифікатор розрахунку
  private UUID userId; // Ідентифікатор користувача, якому належить розрахунок
  private String buildingType; // Тип будівлі
  private double area; // Площа будівлі
  private double perimeter; // Периметр будівлі
  private List<Material> results; // Список результатів розрахунків

  // Конструктор без параметрів (для використання бібліотек, таких як Jackson)
  public Calculation() {}

  // Конструктор з параметрами
  public Calculation(UUID id, UUID userId, String buildingType, double area, double perimeter, List<Material> results) {
    this.id = id;
    this.userId = userId;
    this.buildingType = buildingType;
    this.area = area;
    this.perimeter = perimeter;
    this.results = results != null ? results : new ArrayList<>();
  }

  // Геттери та сеттери
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getBuildingType() {
    return buildingType;
  }

  public void setBuildingType(String buildingType) {
    this.buildingType = buildingType;
  }

  public double getArea() {
    return area;
  }

  public void setArea(double area) {
    this.area = area;
  }

  public double getPerimeter() {
    return perimeter;
  }

  public void setPerimeter(double perimeter) {
    this.perimeter = perimeter;
  }

  public List<Material> getResults() {
    return results;
  }

  public void setResults(List<Material> results) {
    this.results = results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Calculation that = (Calculation) o;
    return Double.compare(that.area, area) == 0 &&
        Double.compare(that.perimeter, perimeter) == 0 &&
        Objects.equals(id, that.id) &&
        Objects.equals(userId, that.userId) &&
        Objects.equals(buildingType, that.buildingType) &&
        Objects.equals(results, that.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, buildingType, area, perimeter, results);
  }

  // Зручне текстове представлення об'єкта
  @Override
  public String toString() {
    return "Calculation{" +
        "id='" + id + '\'' +
        ", userId='" + userId + '\'' +
        ", buildingType='" + buildingType + '\'' +
        ", area=" + area +
        ", perimeter=" + perimeter +
        ", results=" + results +
        '}';
  }
}
