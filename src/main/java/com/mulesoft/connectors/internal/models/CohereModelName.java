package com.mulesoft.connectors.internal.models;

enum CohereModelName {

    command_r7b_12_2024("command-r7b-12-2024"),
    command_r_plus_08_2024("command-r-plus-08-2024"),
    command_r_plus("command-r-plus"),
    command_r("command-r"),
    command("command"),
    command_r_plus_04_2024("command-r-plus-04-2024");

    private final String value;

    CohereModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
