package io.github.rushuat.ocell.field;

public interface ValueConverter<M, D> {

  M toModel(D value);

  D toDocument(M value);
}
