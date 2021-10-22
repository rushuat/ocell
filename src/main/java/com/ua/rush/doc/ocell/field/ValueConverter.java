package com.ua.rush.doc.ocell.field;

public interface ValueConverter<I, O> {

  I convertInput(O value);

  O convertOutput(I value);
}
