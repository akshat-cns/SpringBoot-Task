package com.example.crud_task.controller;

import com.example.crud_task.entity.TableEntity;
import com.example.crud_task.service.JWTService;
import com.example.crud_task.service.TableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TableController.class)
public class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableService tableService;

    @MockBean
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization

    private TableEntity table1;
    private TableEntity table2;

    @BeforeEach
    public void setup() {
        table1 = new TableEntity(1L, "Table 1", 4, true);
        table2 = new TableEntity(2L, "Table 2", 6, false);
    }

    // GET /api/tables
    @Test
    public void testGetTables_HappyPath() throws Exception {
        Mockito.when(tableService.getAllTables()).thenReturn(Arrays.asList(table1, table2));

        mockMvc.perform(get("/api/tables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Table 1"))
                .andExpect(jsonPath("$[0].capacity").value(4))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Table 2"));
    }

    // PATCH /api/tables/{id}
    @Test
    public void testUpdateTableAvailability_HappyPath() throws Exception {
        Mockito.when(tableService.updateTableAvailability(1L, false))
                .thenReturn(Optional.of(new TableEntity(1L, "Table 1", 4, false)));

        mockMvc.perform(patch("/api/tables/1")
                        .param("isAvailable", "false"))
                .andExpect(status().isOk())
                .andExpect(content().string("Table availability updated successfully."));
    }

    @Test
    public void testUpdateTableAvailability_UnhappyPath() throws Exception {
        Mockito.when(tableService.updateTableAvailability(999L, false)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/api/tables/999")
                        .param("isAvailable", "false"))
                .andExpect(status().isNotFound());
    }

    // POST /api/tables
    @Test
    public void testAddTable_HappyPath() throws Exception {
        Mockito.when(tableService.addTable(any(TableEntity.class))).thenReturn(table1);

        mockMvc.perform(post("/api/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(table1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Table 1"))
                .andExpect(jsonPath("$.capacity").value(4));
    }
}