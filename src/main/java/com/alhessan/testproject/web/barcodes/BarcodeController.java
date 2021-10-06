package com.alhessan.testproject.web.barcodes;

import com.alhessan.testproject.web.User;
import com.alhessan.testproject.web.UserRepository;
import com.alhessan.testproject.web.helpers.ZXingHelper;
//import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = {"barcode"})
public class BarcodeController {
    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private UserRepository userRepository;

        @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
        public String viewHomePage(Model model, String userId) {
            if( userId != null) {
                List<Barcode> list = barcodeRepository.findByUserId( Long.parseLong( userId));
                model.addAttribute("listBarcodes", list);
            }else {
                List<Barcode> listBarcodes = (List<Barcode>) barcodeRepository.findAll();
                model.addAttribute("listBarcodes", listBarcodes);
            }

            List<User> listUsers = userRepository.findAll();
            model.addAttribute("listUsers", listUsers);

            return "barcode/index";
        }

    @RequestMapping(value = "barcode-image/{id}", method = RequestMethod.GET)
    public void barcode(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingHelper.getBarCodeImage(id, 200, 200));
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/new")
    public String showNewBarcodePage(Model model) {

        Barcode barcode = new Barcode();
        model.addAttribute("barcode", barcode);
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "barcode/new_barcode";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBarcode(@ModelAttribute("barcode") Barcode barcode) {
        barcodeRepository.save(barcode);

        return "redirect:/barcode";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditBarcodePage(@PathVariable(name = "id") String id) {
        ModelAndView mav = new ModelAndView("barcode/edit_barcode");
        List<User> listUsers = userRepository.findAll();
        mav.addObject("listUsers", listUsers);
        Barcode barcode = barcodeRepository.findById(id).get();
        mav.addObject("barcode", barcode);

        return mav;
    }

    @PostMapping(value = {"/", ""})
    public ModelAndView filterByUser(@RequestBody int userId) {
        ModelAndView mav = new ModelAndView("barcode/index");
        List<Barcode> listBarcodes = barcodeRepository.findByUserId(userId);

        mav.addObject("barcodes", listBarcodes);
        List<User> listUsers = userRepository.findAll();
        mav.addObject("listUsers", listUsers);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") String id) {
        barcodeRepository.deleteById(id);
        return "redirect:/barcode/";
    }
}
