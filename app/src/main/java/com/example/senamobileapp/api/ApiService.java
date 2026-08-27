package com.example.senamobileapp.api;

import com.example.senamobileapp.model.Estudiante;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ApiService {

    // Cambia "/api/estudiantes" según las rutas definidas en tu Controller de Spring Boot
    @GET("api/estudiantes")
    Call<List<Estudiante>> getEstudiantes();

    @POST("api/estudiantes")
    Call<Estudiante> crearEstudiante(@Body Estudiante estudiante);
}