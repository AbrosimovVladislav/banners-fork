import {
  PasswordInput,
  Checkbox,
  Anchor,
  Paper,
  Title,
  Container,
  Group,
  Button,
  Input,
  Text
} from '@mantine/core';
import {useState} from "react";

interface AuthenticationProps {
  setAuthenticated?: (newValue: boolean) => void,
}

export function Authentication({setAuthenticated}: AuthenticationProps) {

  const [login, setLogin] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [incorrectPassword, setIncorrectPassword] = useState<boolean>(false);

  const handleSignIn = () => {
    if (login === "GodBless" && password === "America") {
      setAuthenticated && setAuthenticated(true);
    } else {
      setIncorrectPassword(true);
    }
  }

  return (
      <Container size={420} my={40}>
        <Title ta="center">
          Welcome back!
        </Title>

        <Paper withBorder shadow="md" p={30} mt={30} radius="md">
          <Input
              required
              placeholder={login}
              value={login}
              onChange={(event) => setLogin(event.target.value)}/>
          <PasswordInput
              onChange={(event) => setPassword(event.target.value)}
              value={password}
              label="Password"
              placeholder="Your password"
              required mt="md"/>
          <Text style={{display: incorrectPassword ? '' : 'none'}} color="red">Incorrect
            Credentials</Text>
          <Group mt="lg">
            <Checkbox label="Remember me"/>
            <Anchor component="button" size="sm">
              Forgot password?
            </Anchor>
          </Group>
          <Button onClick={handleSignIn} fullWidth variant="light" mt="xl">
            Sign in
          </Button>
        </Paper>
      </Container>
  );
}