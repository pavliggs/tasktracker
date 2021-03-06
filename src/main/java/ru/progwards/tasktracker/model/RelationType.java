package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * value object - объект бизнес логики (тип связи)
 *
 * @author Oleg Kiselev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@ToString(of = {"id"})
@Table(name = "relation_type")
public class RelationType {

    @Id
    @SequenceGenerator(name = "RelationTypeSeq", sequenceName = "relation_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "RelationTypeSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    private RelationType counterRelation;

    @OneToMany(mappedBy = "relationType", fetch = FetchType.LAZY)
    private List<RelatedTask> relatedTasks = new ArrayList<>();

}
