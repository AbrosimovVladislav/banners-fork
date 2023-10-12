import {GenerationByPromptRequestData, GenerationByUrlRequestData} from "@/constants/interface";
import {post} from "@/service/rest-client";
import {useMutation} from "react-query";

export const useGenerateImagesByUrl = () => {
  return useMutation((requestData: GenerationByUrlRequestData) => {
    const url = process.env.API_URL + "banners/by-url/";
    return post(url, requestData);
  });
};

export const useGenerateImagesByPrompt = () => {
  return useMutation((requestData: GenerationByPromptRequestData) => {
    const url = process.env.API_URL + "banners/by-prompt/";
    return post(url, requestData);
  });
};