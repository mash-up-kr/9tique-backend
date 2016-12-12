package kr.co.mash_up.builder;


import kr.co.mash_up.nine_tique.domain.Category;

public class CategoryBuilder {

    private Long id;

    private String main;

    private String sub;


//    private Set<Product> products = new HashSet<>();

    public CategoryBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder withMain(String main) {
        this.main = main;
        return this;
    }

    public CategoryBuilder withSub(String sub) {
        this.sub = sub;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setId(id);
        category.setMain(main);
        category.setSub(sub);

        return category;
    }
}
