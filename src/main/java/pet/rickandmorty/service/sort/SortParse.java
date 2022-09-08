package pet.rickandmorty.service.sort;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParse {
    private static final String COLON = ":";
    private static final String SEPARATOR = ";";
    private static final int FIELD_INDEX = 1;
    private static final int DIRECTION_INDEX = 0;

    public static Sort parse(String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(COLON)) {
            String[] sortingFields = sortBy.split(SEPARATOR);
            for (String field : sortingFields) {
                Sort.Order order;
                if (field.contains(COLON)) {
                    String[] fieldsAndDirections = field.split(COLON);
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[FIELD_INDEX]),
                            fieldsAndDirections[DIRECTION_INDEX]);
                } else {
                    order = new Sort.Order(Sort.Direction.ASC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, sortBy);
            orders.add(order);
        }
        return Sort.by(orders);
    }
}
