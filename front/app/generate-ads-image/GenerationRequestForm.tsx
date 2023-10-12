"use client"

// External libraries
import {useEffect, useState} from "react";
import {
  Alert,
  Button,
  Dialog,
  LoadingOverlay,
  Textarea,
  Text,
  ThemeIcon,
  Tooltip
} from '@mantine/core';
import {
  IconAt,
  IconMoodNeutral,
  IconAspectRatio,
  IconAlertCircle,
  IconInfoCircle
} from '@tabler/icons-react';
import isURL from 'validator/lib/isURL';

// Internal modules
import SInput from "@/components/common/SInput";
import SDropdown from "@/components/common/SDropdown";
import {
  useGenerateImagesByPrompt,
  useGenerateImagesByUrl
} from "@/service/image-generation-service";
import {IGeneratedImagesData, useGeneratedImagesStore} from "@/storage/generated-images-store";
import {GenerationByPromptRequestData, GenerationByUrlRequestData} from "@/constants/interface";
import {
  ASPECT_RATIO_MAP, DEFAULT_AI_SYSTEM,
  DEFAULT_ASPECT_RATIO, DEFAULT_MODEL,
  DEFAULT_STYLE, DEFAULT_STYLE_DESCRIPTION, DEFAULT_WIDTH_HEIGHT, STYLES_MODELS_MAP
} from "@/constants/default";

const GenerationRequestForm = () => {

  const [url, setUrl] = useState<string>("");
  const [style, setStyle] = useState<string>(DEFAULT_STYLE);
  const [aspectRatio, setAspectRatio] = useState<string>(DEFAULT_ASPECT_RATIO);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [isGenerateByPromptButtonEnable, setIsGenerateByPromptButtonEnable] = useState<boolean>(false);

  const {
    prompt,
    concept,
    promptRequest,
    updateConcept,
    updatePrompt,
    updatePromptRequest,
    updateImages,
    updateImagesRatioFormat,
    updateWebsite
  } = useGeneratedImagesStore();
  const generateImagesByUrl = useGenerateImagesByUrl();
  const generateImagesByPrompt = useGenerateImagesByPrompt();

  useEffect(() => {
    if (prompt !== null && prompt !== "" && prompt !== undefined && prompt !== "Here will be generated prompt text") {
      setIsGenerateByPromptButtonEnable(true)
    } else {
      setIsGenerateByPromptButtonEnable(false)
    }
  }, [prompt])

  const handleSubmitGenerationByUrl = (): void => {
    if (validate()) {
      setLoading(true);
      setErrorMessage("");
      const modelStyleObject = STYLES_MODELS_MAP.find(e => e.style === style);
      const aspectRationObject = ASPECT_RATIO_MAP.find(e => e.view === aspectRatio);
      const imageGenerationRequest: GenerationByUrlRequestData = {
        "url": url,
        "aiSystem": undefined === modelStyleObject ? DEFAULT_AI_SYSTEM: modelStyleObject.aiSystem,
        "sdModel": undefined === modelStyleObject ? DEFAULT_MODEL : modelStyleObject.model,
        "styleDescription": undefined === modelStyleObject ? DEFAULT_STYLE_DESCRIPTION : modelStyleObject.description,
        "aspectRatio": undefined === aspectRationObject
            ? DEFAULT_WIDTH_HEIGHT
            : aspectRationObject.width + ":" + aspectRationObject.height
      }

      updateWebsite(url)

      generateImagesByUrl.mutate(imageGenerationRequest, {
        onSuccess: (response: IGeneratedImagesData) => {
          updateConcept(response.concept)

          updatePrompt(response.prompt);
          updateImages(response.images);
          updatePromptRequest(response.promptRequest)
          const foundElement = ASPECT_RATIO_MAP.find(e => e.view === aspectRatio);
          const ratioFormat = foundElement ? foundElement.ratioFormat : DEFAULT_ASPECT_RATIO;
          updateImagesRatioFormat(ratioFormat)
        },
        onError: (error: unknown, variables: GenerationByUrlRequestData, context: unknown) => {
          setLoading(false);
          if (error instanceof Error) {
            showError(error.toString())
          }
        },
        onSettled: () => {
          setLoading(false);
        }
      });
    }
  }

  const handleSubmitGenerationByPrompt = (): void => {
    setLoading(true);
    setErrorMessage("");

    const modelStyleObject = STYLES_MODELS_MAP.find(e => e.style === style);
    const aspectRationObject = ASPECT_RATIO_MAP.find(e => e.view === aspectRatio);

    const imageByPromptGenerationRequest: GenerationByPromptRequestData = {
      "prompt": prompt,
      "aiSystem": undefined === modelStyleObject ? DEFAULT_AI_SYSTEM: modelStyleObject.aiSystem,
      "sdModel": undefined === modelStyleObject ? DEFAULT_MODEL : modelStyleObject.model,
      "aspectRatio": undefined === aspectRationObject
          ? DEFAULT_WIDTH_HEIGHT
          : aspectRationObject.width + ":" + aspectRationObject.height
    }

    generateImagesByPrompt.mutate(imageByPromptGenerationRequest, {
      onSuccess: (response: IGeneratedImagesData) => {
        updateImages(response.images);
        const foundElement = ASPECT_RATIO_MAP.find(e => e.view === aspectRatio);
        const ratioFormat = foundElement ? foundElement.ratioFormat : DEFAULT_ASPECT_RATIO;
        updateImagesRatioFormat(ratioFormat)
      },
      onError: (error: unknown, variables: GenerationByPromptRequestData, context: unknown) => {
        setLoading(false);
        if (error instanceof Error) {
          showError(error.toString())
        }
      },
      onSettled: () => {
        setLoading(false);
      }
    })
  }

  const showError = (errorMessage: string) => {
    console.error(errorMessage);
    setErrorMessage(errorMessage);
    setTimeout(() => {
      setErrorMessage("");
    }, 10000);
  }

  const validate = (): boolean => {
    if (!isURL(url, {require_protocol: false})) {
      showError("Error: INVALID_URL")
      return false;
    }
    return true;
  }

  return (
      <div className="my-4">
        <Dialog position={{top: 20, right: 20}} opened={errorMessage !== ""} size="lg" radius="md">
          <Alert icon={<IconAlertCircle size="1rem"/>} title="ERROR!" color="red">
            {errorMessage}
          </Alert>
        </Dialog>
        <LoadingOverlay
            loaderProps={{size: 'xl', color: 'teal', variant: 'bars'}}
            visible={loading}
            overlayBlur={2}/>

        <SInput onChange={setUrl} value={url} label="Insert your Website"
                placeholder="Your Website" icon={<IconAt/>}/>
        <SDropdown label="Select advertisement style"
                   value={style}
                   onChange={setStyle}
                   icon={<IconMoodNeutral/>}
                   variants={STYLES_MODELS_MAP.map(e => e.style)}/>
        <SDropdown label="Aspect Ratio"
                   value={aspectRatio}
                   onChange={setAspectRatio}
                   icon={<IconAspectRatio/>}
                   variants={ASPECT_RATIO_MAP.map(e => e.view)}/>

        <div className="flex justify-center items-center">
          <Button.Group orientation="vertical">
            <Button
                onClick={() => handleSubmitGenerationByUrl()}
                className="mt-12 bg-gradient-to-r from-teal-500 to-blue-500">
              Generate By Url</Button>
            <Button
                onClick={() => handleSubmitGenerationByPrompt()}
                className="mt-12 bg-gradient-to-r from-red-500 to-yellow-500"
                disabled={!isGenerateByPromptButtonEnable}>
              Generate By Prompt</Button>
          </Button.Group>

        </div>

        <div className="mt-6">
          <Text className="font-medium mt-2">
            Concept
            <Tooltip label="Content which was scrapped from source Website (temp field)">
              <ThemeIcon className="ml-1" size="xs" color="teal">
                <IconInfoCircle style={{width: '100%', height: '100%'}}/>
              </ThemeIcon>
            </Tooltip>
          </Text>
          <Textarea value={concept}
                    readOnly
                    styles={{input: {height: '150px'}}}
                    className="text-sm text-slate-500 h-42 -mx-6"/>

          <Text className="font-medium mt-2">
            Prompt Schema
            <Tooltip label="Final content of request to ChatGPT for prompt generation (temp field)">
              <ThemeIcon className="ml-1" size="xs" color="teal">
                <IconInfoCircle style={{width: '100%', height: '100%'}}/>
              </ThemeIcon>
            </Tooltip>
          </Text>
          <Textarea value={promptRequest}
                    readOnly
                    styles={{input: {height: '200px'}}}
                    className="text-sm text-slate-500 h-42 -mx-6"/>
        </div>
      </div>
  )
}

export default GenerationRequestForm;