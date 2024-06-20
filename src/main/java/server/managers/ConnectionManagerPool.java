package server.managers;

import common.interaction.Response;

import java.io.ObjectOutputStream;

public record ConnectionManagerPool(Response response, ObjectOutputStream objectOutputStream){};
