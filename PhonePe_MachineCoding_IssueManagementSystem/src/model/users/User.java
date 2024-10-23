package model.users;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class User {
    @NonNull
    private String emailId;
    @NonNull
    private String name;
}
