import {Input} from '@mantine/core';

interface SInputProps {
  label: string,
  placeholder: string,
  icon: JSX.Element,
  value: string,
  onChange: (value: string) => void;
}

const SInput = ({label, placeholder, icon, value, onChange}: SInputProps) => {
  return (
      <Input.Wrapper
          className="mb-4 w-96"
          label={label}
      >
        <Input
            placeholder={placeholder}
            value={value}
            onChange={(event) => onChange(event.target.value)}
            icon={icon}/>
      </Input.Wrapper>
  )
}

export default SInput;