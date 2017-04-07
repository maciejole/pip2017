package pl.hycom.pip.messanger.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    private static final long serialVersionUID = 9211285852881742074L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 80)
    @Size(min=1, max=80)
    private String name;

    @NotNull
    @Column(length = 80)
    @Size(min=1, max=80)
    private String description;

    @NotNull
    private String imageUrl;

    @ManyToMany(
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.REFRESH
            },
            fetch = FetchType.EAGER
    )
    @OrderColumn
    private Set<Keyword> keywords = new LinkedHashSet<>();

    public boolean containsKeywords(String[] keywordValues) {
        for (String keywordValue : keywordValues) {
            if (keywords.stream()
                    .filter(keyword -> keyword.getWord().equals(keywordValue))
                    .count() == 0) {
                return false;
            }
        }
        return true;
    }
}
