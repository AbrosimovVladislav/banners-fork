import {Image} from '@mantine/core';

interface SImageProps {
  src: string,
  height: number
}

const SImage = ({src, height}: SImageProps) => {

  return (
      <Image
          src={src}
          height={height}
      />
  )

}

export default SImage;