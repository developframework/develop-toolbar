package develop.toolbar.structs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeadTime {

    private String text;

    private int value;

    @Override
    public String toString() {
        return text;
    }
}
