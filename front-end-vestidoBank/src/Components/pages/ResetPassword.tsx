import { useState } from "react";

export function ResetPassword() {

  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");

  if(!password || !confirmPassword) {
    setError("as senhas não coincidem")
  }

  

  return (
    <div>

    </div>
  )
}