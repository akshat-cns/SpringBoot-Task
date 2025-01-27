package com.example.crud_task.controller;

import com.example.crud_task.entity.TableEntity;
import com.example.crud_task.service.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // GET /api/tables
    @GetMapping
    public List<TableEntity> getTables() {
        return tableService.getAllTables();
    }

    // PATCH /api/tables/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTableAvailability(
            @PathVariable Long id,
            @RequestParam boolean isAvailable) {
        Optional<TableEntity> updatedTable = tableService.updateTableAvailability(id, isAvailable);
        if (updatedTable.isPresent()) {
            return ResponseEntity.ok("Table availability updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/tables
    @PostMapping
    public ResponseEntity<TableEntity> addTable(@RequestBody TableEntity table) {
        TableEntity newTable = tableService.addTable(table);
        return ResponseEntity.ok(newTable);
    }

    // PUT /api/tables/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TableEntity> updateTable(
            @PathVariable Long id,
            @RequestBody TableEntity updatedTable) {
        Optional<TableEntity> table = tableService.updateTable(id, updatedTable);
        if (table.isPresent()) {
            return ResponseEntity.ok(table.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tables/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(@PathVariable Long id) {
        if (tableService.deleteTable(id)) {
            return ResponseEntity.ok("Table deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}