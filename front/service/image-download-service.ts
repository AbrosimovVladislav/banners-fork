import {useMutation} from "react-query";
import {getFile} from "@/service/rest-client";

export const useDownloadImage = () => {
  return useMutation((imageUrl: string) => {
    const url = process.env.API_URL + "image" + "?imageUrl=" + imageUrl;
    return getFile(url);
  });
};

export const useDownloadImages = () => {
  return useMutation((imageUrls: string[]) => {
    const url = process.env.API_URL + "image/zip" + "?imageUrls=" + imageUrls;
    return getFile(url);
  });
};