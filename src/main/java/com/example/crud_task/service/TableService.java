package com.example.crud_task.service;

import com.example.crud_task.entity.TableEntity;
import com.example.crud_task.exception.TableNotFoundException;
import com.example.crud_task.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<TableEntity> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<TableEntity> getTableById(Long id) {
        return tableRepository.findById(id);
    }

    public TableEntity addTable(TableEntity table) {
        return tableRepository.save(table);
    }

    public Optional<TableEntity> updateTable(Long id, TableEntity updatedTable) {
        TableEntity existingTable = tableRepository.findById(id)
                .orElseThrow(() -> new TableNotFoundException("Table with ID " + id + " not found."));
        existingTable.setName(updatedTable.getName());
        existingTable.setCapacity(updatedTable.getCapacity());
        existingTable.setAvailable(updatedTable.isAvailable());
        return Optional.of(tableRepository.save(existingTable));
    }

    public boolean deleteTable(Long id) {
        if (!tableRepository.existsById(id)) {
            throw new TableNotFoundException("Table with ID " + id + " not found.");
        }
        tableRepository.deleteById(id);
        return true;
    }

    public Optional<TableEntity> updateTableAvailability(Long id, boolean isAvailable) {
        TableEntity table = tableRepository.findById(id)
                .orElseThrow(() -> new TableNotFoundException("Table with ID " + id + " not found."));
        table.setAvailable(isAvailable);
        return Optional.of(tableRepository.save(table));
    }
}