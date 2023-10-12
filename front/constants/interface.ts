export interface GenerationByUrlRequestData {
  "url": string,
  "aiSystem": string,
  "sdModel": string,
  "styleDescription": string,
  "aspectRatio": string,
  "numInferenceSteps"?: string,
  "loraModel"?: string,
  "loraModelStrength"?: string,
  "embeddingsModel"?: string,
  "seed"?: string,
  "scheduler"?: string,
  "clipSkip"?: string
  "negativePrompt"?: string
}

export interface GenerationByPromptRequestData {
  "prompt": string,
  "aiSystem": string,
  "sdModel": string,
  "aspectRatio": string,
  "numInferenceSteps"?: string,
  "loraModel"?: string,
  "loraModelStrength"?: string,
  "embeddingsModel"?: string,
  "seed"?: string,
  "scheduler"?: string,
  "clipSkip"?: string
  "negativePrompt"?: string
}