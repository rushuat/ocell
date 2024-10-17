package io.github.rushuat.ocell.field;

public interface ValueConverter<M, D> {

  M toModel(D value) throws Exception;

  D toDocument(M value) throws Exception;
}
