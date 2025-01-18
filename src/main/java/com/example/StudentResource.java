package com.example;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Path("/api/student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private static final Logger LOGGER = Logger.getLogger(StudentResource.class.getName());
    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Retrieve all students",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.ARRAY, implementation = StudentDto.class))
            )
    })
    public Response getAllStudents() {
        LOGGER.info("Fetching all students");
        return ResponseHandler.generateResponse(
                200, "Students retrieved successfully", studentService.findAll());
    }


    @GET
    @Path("/{classes}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Retrieve Student by Classes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = StudentDto.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Student not found for the provided Classes",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response getStudentByClasses(@PathParam("classes") String classes) {
        LOGGER.info("Fetching Student by Classes" + classes);
        return studentService.findByClasses(classes)
                .map(studentDto -> ResponseHandler.generateResponse(200,"Student retrieved succesfully",studentDto))
                .orElseGet(() -> ResponseHandler.generateResponse(404,"Student not found","{}"));
    }


    @GET
    @Path("/{nim}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Retrieve student by NIM",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = StudentDto.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Student not found for the provided NIM",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response getStudentByNim(@PathParam("nim") String nim) {
        LOGGER.info("Fetching student with NIM: " + nim);
        return studentService.findByNim(nim)
                .map(student -> ResponseHandler.generateResponse(200, "Student retrieved successfully", student))
                .orElseGet(() -> ResponseHandler.generateResponse(404, "Student not found", "{}"));
    }

    @POST
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = StudentDto.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid student data",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response createStudent(@Valid StudentDto studentDto) {
        LOGGER.info("Creating student: " + studentDto);
        try {
            StudentDto createdStudent = studentService.save(studentDto);
            return ResponseHandler.generateResponse(201, "Student created successfully", createdStudent);
        } catch (ServiceException e) {
            LOGGER.severe("Error creating student: " + e.getMessage());
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @PUT
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Student updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = StudentDto.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid student data",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response updateStudent(@Valid StudentDto studentDto) {
        LOGGER.info("Updating student: " + studentDto);
        try {
            StudentDto updatedStudent = studentService.update(studentDto);
            return ResponseHandler.generateResponse(200, "Student updated successfully", updatedStudent);
        } catch (ServiceException e) {
            LOGGER.severe("Error updating student: " + e.getMessage());
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @DELETE
    @Path("/{nim}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Student deleted successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response deleteStudent(@PathParam("nim") String nim) {
        LOGGER.info("Deleting student with NIM: " + nim);
        if (studentService.deleteByNim(nim)) {
            return ResponseHandler.generateResponse(200, "Student deleted successfully", "{}");
        } else {
            return ResponseHandler.generateResponse(404, "Student not found", "{}");
        }
    }
}
