package com.alhessan.testproject.web.barcodes;

import com.alhessan.testproject.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khalid Elshafie <abolkog@gmail.com>
 * @Created 09/09/2018 10:08 AM.
 */
@RestController
@RequestMapping(value = "/api/barcodes")
public class BarcodeAPIController extends BaseController {

    @Autowired
    private BarcodeRepository barcodeRepository;


    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<Barcode>> getAllBarcodes(Model model, String userId) {
        List<Barcode> result = new ArrayList<>();
        if( userId != null) {
           result = barcodeRepository.findByUserId( Long.parseLong( userId));
        }else {
            result = (List<Barcode>) barcodeRepository.findAll();
        }

//        List<Barcode> result =  barcodeRepository.findByUserId(getCurrentUser().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barcode> getBarcodeById(@PathVariable  String id) {
        Barcode result =  barcodeRepository.findById(id).get();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }



    @PostMapping(value = {"", "/"})
    public ResponseEntity<Barcode> createNewBarcode(@Valid @RequestBody Barcode barcode) {
        barcode.setUserId(getCurrentUser().getId());
        Barcode result = barcodeRepository.save(barcode);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarcode(@PathVariable String id) {

        barcodeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
