package com.bryzgalov.analyzer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Athlete {
  @Id
  @Column(name = "person_id")
  @GeneratedValue
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "person_id")
  private Person person;

  @Column(name = "bench_press")
  private Long benchPress;

  public Athlete(Person person, Long benchPress) {
    this.person = person;
    this.benchPress = benchPress;
  }
}
