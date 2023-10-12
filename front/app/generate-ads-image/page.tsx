"use client"


import GeneratedInfoCard from "@/app/generate-ads-image/GeneratedInfoCard";
import GenerationRequestForm from "@/app/generate-ads-image/GenerationRequestForm";

const GenerateAdsImage = () => {
  return (
      <div className="p-2 m-1 flex flex-row">

        <div className="ml-8">
          <GenerationRequestForm/>
        </div>


        <div className="m-4 ml-20">
          <GeneratedInfoCard/>
        </div>

      </div>
  )
}

export default GenerateAdsImage;