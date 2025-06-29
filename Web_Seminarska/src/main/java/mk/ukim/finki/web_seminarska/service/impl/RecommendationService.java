package mk.ukim.finki.web_seminarska.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.web_seminarska.model.Appointment;
import mk.ukim.finki.web_seminarska.model.Uslugi;
import mk.ukim.finki.web_seminarska.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    public List<Map<String, Object>> getRecommendations(Long userId) throws IOException, InterruptedException {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Имам листа со термини за еден корисник во формат:\n");
        for (Appointment a : appointments) {
            String salonName = a.getSalon() != null ? a.getSalon().getName() : "непознат";
            String services = a.getServices() != null
                    ? a.getServices().stream().map(Uslugi::getName).collect(Collectors.joining(", "))
                    : "нема";

            promptBuilder.append(String.format("Салон: %s, Време: %s, Услуга: %s\n",
                    salonName, a.getStart_time(), services));
        }

        String prompt = promptBuilder.toString().trim();
        if (prompt.isEmpty()) {
            throw new IllegalArgumentException("Prompt е празен. Нема термини за корисникот.");
        }

        prompt += "\nВрз основа на овие термини, испрати ми исклучиво JSON листа од 3 препораки за нови услуги кои би му се допаднале на корисникот. Форматот:\n";
        prompt += "[{\"salon\": \"ИмеНаСалон\", \"service\": \"ИмеНаУслуга\"}]\n";
        prompt += "Само JSON, без дополнителен текст.";

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        String json = mapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println("Gemini response: " + response.body());

        JsonNode root = mapper.readTree(response.body());
        String generatedText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();


        generatedText = generatedText.replaceAll("(?s)```json\\s*", "").replaceAll("(?s)```", "").trim();


        return mapper.readValue(generatedText, new TypeReference<List<Map<String, Object>>>() {});


    }
}



