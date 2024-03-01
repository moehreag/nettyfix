package io.github.moehreag.nettyfix.mixin;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PacketByteBuf.class)
public class PacketByteBufMixin {

	@Redirect(method = "readString", at = @At(value = "INVOKE", target = "Lio/netty/buffer/ByteBuf;array()[B", remap = false))
	private byte[] readArrayDirect(ByteBuf instance){

		if (instance.hasArray()){
			return instance.array();
		}

		byte[] bytes = new byte[instance.readableBytes()];
		instance.readBytes(bytes);
		instance.release();
		return bytes;
	}
}
