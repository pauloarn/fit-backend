package com.bolo.fit.model;


import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BodyPart", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class BodyPart extends BaseEntity {
    @Id
    @SequenceGenerator(name = "idbodypart_id_seq",
            sequenceName = "idbodypart_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "idbodypart_id_seq")
    @Column(name = "id")
    private Long bodyPartId;

    @Column(name = "name")
    private String name;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "bodyPart", cascade = CascadeType.ALL)
    private List<Exercise> exercise;
}
