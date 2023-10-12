import {NativeSelect} from '@mantine/core';

interface SDropdownProps {
  label: string,
  variants: string[],
  value: string,
  onChange: (value: string) => void;
  icon: JSX.Element
}

const SDropdown = ({label, variants, value, onChange, icon}: SDropdownProps) => {
  return (
      <NativeSelect
          icon={icon}
          value={value}
          onChange={(event) => onChange(event.target.value)}
          className="mb-4 w-56"
          data={variants}
          label={label}
          radius="md"
      />
  )
}

export default SDropdown;