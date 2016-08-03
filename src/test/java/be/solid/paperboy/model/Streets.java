package be.solid.paperboy.model;

import com.google.common.collect.ImmutableSet;

import static com.google.common.collect.ImmutableSet.of;

public class Streets {

    public static final String ELM_STREET = "ELM_STREET";
    public static final String SEASAM_STREET = "SEASAM_STREET";
    public static final String ROUTE666 = "ROUTE666";

    public static final ImmutableSet<String> ALL_STREETS = of(ELM_STREET, SEASAM_STREET, ROUTE666);

    private Streets() {
    }
}
