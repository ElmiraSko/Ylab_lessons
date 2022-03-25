package com.erasko;

import com.erasko.model.MyMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // для перехода на главную страницу со страницы read_file
    @GetMapping("toWelcome")
    public String goToUser() {
        return "redirect:/";
    }

    @PostMapping(value="/uploadFile", headers = "content-type=multipart/*")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model){

        String fileName = null;
        long id = 0L;
        System.out.println("Метод контроллера по загрузке файла \n");
        if (!file.isEmpty()) { //file - это набор байтов
            String rootPath = "D:/testFiles/";   // каталог для хранения файла
            String relativePath = "external_game_files";

            String rootPath3 = System.getProperty("catalina.home");
            File dir = new File(rootPath3 + File.separator + relativePath);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            String uplF = dir.getAbsolutePath();
            try {
                byte[] bytes = file.getBytes();
                fileName = file.getOriginalFilename();  // получили настоящее имя файла
                System.out.println("ContentType файла - " + file.getContentType());
                System.out.println("получили байты, их размер = " + bytes.length + ", " + fileName);
                id = (long) (Math.random()*100) + 1;

                File upload = new File(uplF + File.separator + id + fileName);
                System.out.println("===============");
                System.out.println(upload);

                // открыли поток для записи в указанный файл
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(upload));
                stream.write(bytes);
                stream.flush();
                stream.close();
                model.addAttribute("my_message",
                        new MyMessage("Вы удачно загрузили файл.", fileName));
            } catch (Exception e) {
                model.addAttribute("my_message",
                        new MyMessage("Вам не удалось загрузить файл." + e.getMessage()));
                e.printStackTrace();
            }
        } else {
            model.addAttribute("my_message",
                    new MyMessage("Вам не удалось загрузить файл, потому что файл пустой."));
        }
        return "read_file";
    }
}
