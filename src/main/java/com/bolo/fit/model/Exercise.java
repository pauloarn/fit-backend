package com.bolo.fit.model;

import com.bolo.fit.config.Catalog;
import com.bolo.fit.config.Schema;
import com.bolo.fit.service.dto.response.ImageBase64ResponseDTO;
import com.bolo.fit.utils.ExercisesGifUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Exercises", catalog = Catalog.FIT_DATA_BASE, schema = Schema.FitApp)
public class Exercise extends BaseEntity {
    @Id
    @SequenceGenerator(name = "idexercises_id_seq",
            sequenceName = "idexercises_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "idexercises_id_seq")
    @Column(name = "id")
    private Long execiseId;

    @Column(name = "gifurl")
    private String gifUrl;

    @Column(name = "page")
    private Integer page;

    @Column(name = "item")
    private Integer item;

    @Column(name = "name")
    private String name;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "bodypart_fk")
    private BodyPart bodyPart;

    @ManyToOne
    @JoinColumn(name = "equipmenttype_fk")
    private EquipmentType equipmentType;

    @ManyToOne
    @JoinColumn(name = "exercisetype_fk")
    private ExerciseType exerciseType;

    public ImageBase64ResponseDTO getExerciseGifData () throws IOException {
        ImageBase64ResponseDTO imageData = new ImageBase64ResponseDTO();
        imageData.setImageBase64(ExercisesGifUtils.getGifBase64(this.getGifUrl()));
        imageData.setImageType("gif");
        return imageData;
    }
}
