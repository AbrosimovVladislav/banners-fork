import {create} from "zustand";
import {
  DEFAULT_CONCEPT,
  DEFAULT_IMAGE,
  DEFAULT_PROMPT,
  DEFAULT_PROMPT_REQUEST
} from "@/constants/default";

export interface IGeneratedImagesData {
  concept: string,
  prompt: string,
  promptRequest: string,
  images: string[]
}

interface IGeneratedImages {
  concept: string
  updateConcept: (newConcept: string) => void
  prompt: string
  updatePrompt: (newPrompt: string) => void
  promptRequest: string
  updatePromptRequest: (newPromptRequest: string) => void
  images: string[]
  updateImages: (newImages: string[]) => void
  imagesRatioFormat: string
  updateImagesRatioFormat: (newImagesRatioFormat: string) => void
  website: string
  updateWebsite: (newWebsite: string) => void
}

export const useGeneratedImagesStore = create<IGeneratedImages>((set) => ({
      concept: DEFAULT_CONCEPT,
      updateConcept: (newConcept: string) => set({concept: newConcept}),
      prompt: DEFAULT_PROMPT,
      updatePrompt: (newPrompt: string) => set({prompt: newPrompt}),
      promptRequest: DEFAULT_PROMPT_REQUEST,
      updatePromptRequest: (newPromptRequest: string) => set({promptRequest: newPromptRequest}),
      images: [DEFAULT_IMAGE, DEFAULT_IMAGE, DEFAULT_IMAGE, DEFAULT_IMAGE],
      updateImages: (newImages: string[]) => set({images: newImages}),
      imagesRatioFormat: "square",
      updateImagesRatioFormat: (newImagesRatioFormat: string) => set({imagesRatioFormat: newImagesRatioFormat}),
      website: "Your Website",
      updateWebsite: (newWebsite: string) => set({website: newWebsite})
    })
);

