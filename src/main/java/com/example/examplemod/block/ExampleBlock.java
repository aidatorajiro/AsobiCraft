package com.example.examplemod.block;

public class ExampleBlock extends BaseBlock {
    public ExampleBlock() {
        super();
        this.properties.setLightLevel((x) -> 9);
    }
}
