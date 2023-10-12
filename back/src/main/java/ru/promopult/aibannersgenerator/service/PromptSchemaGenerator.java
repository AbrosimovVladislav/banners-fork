package ru.promopult.aibannersgenerator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PromptSchemaGenerator {

  @Value("${default-prompt-schema}")
  private String DEFAULT_PROMPT_SCHEMA;

//  private static final String DEFAULT_PROMPT_SCHEMA = """
//      {conceptRequest}
//      Based on concept which you will generate
//      Imagine you are an AI artist and create a promotional picture for a website, fit a prompt for introduction to Stable Diffusion after analyzing the website. Give an answer agree to the rules below and don't give any other comments:
//      Generate an "imagine prompt" that contains a maximum word count of 1,000 words that will be used as input for an AI-based text to image program called Stable Diffusion based on the following parameters: /imagine prompt: [1], [2], [3], [4], [5], [6]
//      In this prompt, [1] should be replaced with a subject related to the service from the site and [2] should be a short concise description about that subject. Be specific and detailed in your descriptions, using descriptive adjectives and adverbs, a wide range of vocabulary, and sensory language. Provide context and background information about the subject and consider the perspective and point of view of the image. Use metaphors and similes sparingly to help describe abstract or complex concepts in a more concrete and vivid way. Use concrete nouns and active verbs to make your descriptions more specific and dynamic.
//      [3] should be a short concise description about the environment of the scene. Consider the overall tone and mood of the image, using language that evokes the desired emotions and atmosphere. Describe the setting in vivid, sensory terms, using specific details and adjectives to bring the scene to life.
//      [4] should be a short concise description about the mood of the scene. Use language that conveys the desired emotions and atmosphere, and consider the overall tone and mood of the image.
//      [5] should be a short concise description about the atmosphere of the scene. Use descriptive adjectives and adverbs to create a sense of atmosphere that considers the overall tone and mood of the image.
//      [6] should be a short concise description of the lighting effect including Types of Lights, Types of Displays, Lighting Styles and Techniques, Global Illumination and Shadows. Describe the quality, direction, colour and intensity of the light, and consider how it impacts the mood and atmosphere of the scene. Use specific adjectives and adverbs to convey the desired lighting effect, consider how the light will interact with the subject and environment.
//      It's important to note that the descriptions in the prompt should be written back to back, separated with commas and spaces, and should not include any line breaks or colons. Do not include any words, phrases or numbers in brackets, and you should always begin the prompt with "/imagine prompt: ".
//      Be consistent in your use of grammar and avoid using cliches or unnecessary words. Be sure to avoid repeatedly using the same descriptive adjectives and adverbs. Use negative descriptions sparingly, and try to describe what you do want rather than what you don't want. Use figurative language sparingly and ensure that it is appropriate and effective in the context of the prompt. Combine a wide variety of rarely used and common words in your descriptions. Don't include specific facts about the service in your description. Do not write the country of origin of the service provider in the prompt.
//      The "imagine prompt" have a main goal to present a service or a specific product in such a way that customers would want to buy them. It should also shape the impact on the reaction of buyers, encouraging them to take action.
//      The "imagine prompt" should strictly contain under 1,500 words.
//      Return information in JSON format like
//      {
//        "concept": "//place generated concept here",
//        "prompt": "//place prompt here"
//      }
//      """;

  public String getDefaultPromptSchema() {
    return DEFAULT_PROMPT_SCHEMA;
  }
}
