import {
  Card,
  Text,
  Badge,
  Button,
  Group,
  Dialog,
  Alert,
  Textarea,
  Tooltip,
  ThemeIcon
} from '@mantine/core';
import {useGeneratedImagesStore} from "@/storage/generated-images-store";
import SImage from "@/components/common/SImage";
import {IconAlertCircle, IconInfoCircle} from '@tabler/icons-react';
import {useDownloadImages} from "@/service/image-download-service";
import {useEffect, useState} from "react";

interface GeneratedProps {
  generationStatus?: string,
}

const GeneratedInfoCardAdmin = ({generationStatus}: GeneratedProps) => {

  const {concept, prompt, updatePrompt, images, imagesRatioFormat, website} = useGeneratedImagesStore();

  const [errorMessage, setErrorMessage] = useState<string>("");
  const [promptView, setPromptView] = useState<string>(prompt);
  const [isSavePromptButtonVisible, setIsSavePromptButtonVisible] = useState<boolean>(false);

  const downloadImageMutation = useDownloadImages();

  useEffect(() => {
    setPromptView(prompt)
  }, [prompt])

  const handlePromptTextAreaChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setPromptView(event.currentTarget.value)
    setIsSavePromptButtonVisible(true);
  }

  const handleSavePrompt = () => {
    updatePrompt(promptView);
    setIsSavePromptButtonVisible(false)
  }

  const handleDownloadAll = async () => {
    downloadImageMutation.mutate(images, {
      onSuccess: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = "images.zip";
        document.body.appendChild(a);
        a.click();
        a.remove();
      },
      onError: (error: unknown) => {
        if (error instanceof Error) {
          showError(error.toString())
        }
      },
      onSettled: () => {
      }
    });
  };

  const showError = (errorMessage: string) => {
    console.error(errorMessage);
    setErrorMessage(errorMessage);
    setTimeout(() => {
      setErrorMessage("");
    }, 10000);
  }

  return (
      <Card className="shadow-sm p-5 rounded-lg border-2">
        <Dialog position={{top: 20, right: 20}} opened={errorMessage !== ""} size="lg" radius="md">
          <Alert icon={<IconAlertCircle size="1rem"/>} title="ERROR!" color="red">
            {errorMessage}
          </Alert>
        </Dialog>{
        imagesRatioFormat === "square"
            ? <Card.Section className="flex flex-row">
              <div className="m-1 pl-3">
                <SImage src={images[0]} height={250}/>
                <SImage src={images[1]} height={250}/>
              </div>
              <div className="m-1 pl-3">
                <SImage src={images[2]} height={250}/>
                <SImage src={images[3]} height={250}/>
              </div>
            </Card.Section>
            : <Card.Section className="flex flex-row">
              <div className="m-1 pl-20">
                <SImage src={images[0]} height={200}/>
                <SImage src={images[1]} height={200}/>
                <SImage src={images[2]} height={200}/>
                <SImage src={images[3]} height={200}/>
              </div>
            </Card.Section>
      }

        <Group className="mt-2 mb-5" position="apart">
          <div className="flex flex-row">
            <Text className="font-medium">Website:</Text>
            <Text className="mx-2 text-teal-500 font-semibold">{website}</Text>
          </div>

          <Badge className={isSavePromptButtonVisible ? "text-red-500" : "text-teal-500"}>
            {isSavePromptButtonVisible ? 'Prompt not saved!' : 'Prompt saved'}
          </Badge>
        </Group>

        <div>
          <Text className="font-medium mt-2">
            Prompt
            <Tooltip label="Final content of request to SD for image generation">
              <ThemeIcon className="ml-1" size="xs" color="teal">
                <IconInfoCircle style={{width: '100%', height: '100%'}}/>
              </ThemeIcon>
            </Tooltip>
          </Text>
          <Textarea value={promptView}
                    styles={{input: {height: '200px'}}}
                    onChange={(event) => handlePromptTextAreaChange(event)}
                    className="text-sm text-slate-500 h-42"/>
          <Button disabled={!isSavePromptButtonVisible} onClick={handleSavePrompt} variant="light" color="red" mt="sm"
                  radius="md">
            Save Prompt
          </Button>
        </div>


        <Button onClick={handleDownloadAll} variant="light" color="teal" fullWidth mt="sm"
                radius="md">
          Download all images
        </Button>
      </Card>
  )
}

export default GeneratedInfoCardAdmin;