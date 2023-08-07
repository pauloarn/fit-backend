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
@Table(name = "equipment_type", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class EquipmentType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "fitapp.idequipment_type_id_seq",
            sequenceName = "fitapp.idequipment_type_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fitapp.idequipment_type_id_seq")
    @Column(name = "id")
    private Long equipmentTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "equipmentType", cascade = CascadeType.ALL)
    private List<Exercise> exercise;
}
