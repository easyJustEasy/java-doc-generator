package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DocWrapper {
    private String title;
    private DocDTO docDTO;
}
