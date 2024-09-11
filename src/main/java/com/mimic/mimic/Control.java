package com.mimic.mimic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Control {

    String letra;
    
    @PostMapping("/submit")
    public String receivePrediction(@RequestBody Prediction prediction) {
        letra = prediction.getClassName().toString().toUpperCase();
        return prediction.getClassName();
    }

    @GetMapping("/submit")
    public String getPrediction() {
        return letra;
    }

    public static class Prediction {

        private String className;
        private String probability;

        // Getters y setters
        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getProbability() {
            return probability;
        }

        public void setProbability(String probability) {
            this.probability = probability;
        }
    }
}
