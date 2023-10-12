"use client"


import {useState} from "react";
import {Authentication} from "@/app/admin-panel/Authentication";
import GenerationRequestFormAdmin from "@/app/admin-panel/GenerationRequestFormAdmin";
import GeneratedInfoCardAdmin from "@/app/admin-panel/GeneratedInfoCardAdmin";

const AdminPanel = () => {

  const [authenticated, setAuthenticated] = useState(false);

  if(!authenticated){
    return <Authentication setAuthenticated={setAuthenticated}/>
  }

  return (
      <div className="p-2 m-1 flex flex-row">

        <div className="ml-8">
          <GenerationRequestFormAdmin/>
        </div>


        <div className="m-4 ml-20">
          <GeneratedInfoCardAdmin/>
        </div>

      </div>
  )
}

export default AdminPanel;