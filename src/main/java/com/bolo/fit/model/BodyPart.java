package com.bolo.fit.model;


import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BodyPart", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class BodyPart extends BaseEntity {
    @Id
    @SequenceGenerator(name = "fitapp.idbodypart_id_seq",
            sequenceName = "fitapp.idbodypart_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fitapp.idbodypart_id_seq")
    @Column(name = "id")
    private Long bodyPartId;

    @Column(name = "name")
    private String name;

    @Column(name = "nome")
    private String nome;

    @Column(name = "exercises_count")
    private Integer exerciseCount;

    @OneToMany(mappedBy = "bodyPart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercise;
}
